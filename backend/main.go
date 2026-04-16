package main

import (
	"backend/handler"
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

	router := mux.NewRouter()

	router.HandleFunc("/products", h.GetProducts).Methods("GET")
	router.HandleFunc("/products/{id}", h.GetProductById).Methods("GET")
	router.HandleFunc("/registration", h.Registration).Methods("POST")
	router.HandleFunc("/login", h.Login).Methods("POST")
	router.HandleFunc("/cart/{id}", h.GetCartItemsByCartId).Methods("GET")
	router.HandleFunc("/cartItem", h.AddCartItem).Methods("POST")
	router.HandleFunc("/cartItem/{id}", h.DeleteCartItem).Methods("DELETE")
	router.HandleFunc("/", showFunctions)

	port := ":8000"
	fmt.Printf("server is running on: http://localhost%s\n", port)
	log.Fatal(http.ListenAndServe(port, router))
}

func showFunctions(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "products/	GET")
	fmt.Fprintln(w, "products/{id}	GET")
}
