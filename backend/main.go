package main

import (
	"backend/handler"
	"backend/middleware"
	"backend/repository"
	"backend/service"
	"context"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/mux"
	"github.com/jackc/pgx/v5/pgxpool"
	_ "github.com/joho/godotenv/autoload"
)

var (
	err error
)

func main() {
	ctx := context.Background()

	pool, err := pgxpool.New(ctx, os.Getenv("DATABASE_URL"))
	if err != nil {
		log.Fatalf("Failed to connect to the database: %v", err)
	}

	repo := repository.NewRepository(pool)
	s := service.NewService(repo)
	h := handler.NewHandler(s)
	authService := service.NewAuthService(repo, os.Getenv("JWT_KEY"))
	authHandler := handler.NewAuthHandler(authService)
	authMiddleware := middleware.NewAuthMiddleware(authService)

	router := mux.NewRouter()

	router.HandleFunc("/products", h.GetProducts).Methods("GET")
	router.HandleFunc("/products/{id}", h.GetProductById).Methods("GET")
	router.HandleFunc("/registration", authHandler.Registration).Methods("POST")
	router.HandleFunc("/login", authHandler.Login).Methods("POST")
	router.Handle("/cart", authMiddleware.Protect(http.HandlerFunc(h.GetCartItemsByCartId))).Methods("GET")
	router.Handle("/cartItem", authMiddleware.Protect(http.HandlerFunc(h.AddCartItem))).Methods("POST")
	router.HandleFunc("/cartItem/increase/{id}", h.IncreaseCartItem).Methods("PUT")
	router.HandleFunc("/cartItem/decrease/{id}", h.DecreaseCartItem).Methods("PUT")
	router.HandleFunc("/cartItem/{id}", h.DeleteCartItem).Methods("DELETE") //TODO:wrap this into authMiddleware
	router.HandleFunc("/reviews/{product_id}", h.GetReviews).Methods("GET")
	router.HandleFunc("/reviews", h.AddReview).Methods("POST")
	router.Handle("/order", authMiddleware.Protect(http.HandlerFunc(h.CreateOrder))).Methods("POST") //TODO:wrapt this into authMiddleware
	router.Handle("/order", authMiddleware.Protect(http.HandlerFunc(h.GetOrders))).Methods("GET")    //TODO:wrapt this into authMiddleware
	router.HandleFunc("/orderItems/{id}", h.GetOrderItemsByOrderId).Methods("GET")                   //TODO:wrapt this into authMiddleware

	port := ":8000"
	fmt.Printf("server is running on: http://localhost%s\n", port)
	log.Fatal(http.ListenAndServe(port, router))
}
