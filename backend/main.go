package main

import (
	"backend/handlers"
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

	router := mux.NewRouter()

	router.HandleFunc("/products", handlers.GetProducts(pool)).Methods("GET")
	router.HandleFunc("/products/{id}", handlers.GetProductById(pool)).Methods("GET")
	router.HandleFunc("/", showFunctions)

	port := ":8000"
	fmt.Printf("server is running on: http://localhost%s\n", port)
	log.Fatal(http.ListenAndServe(port, router))
}

func showFunctions(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "products/	GET")
	fmt.Fprintln(w, "products/{id}	GET")
}
