package main

import (
	"cloud.google.com/go/firestore"
	"context"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	_ "github.com/joho/godotenv/autoload"
	"log"
	"net/http"
	"os"
	"backend/handlers"
)

var (
	ctx       context.Context
	client    *firestore.Client
	err       error	
	projectId = os.Getenv("project_id")
)

func main() {
	if projectId == "" {
		log.Fatal("projectId is empty. Please write it into .env")
		return
	}

	ctx = context.Background()
	client, err = firestore.NewClient(ctx, projectId)

	if err != nil {
		log.Fatalf("firestore init error: %v\n", err)
	}

	router := mux.NewRouter()

	router.HandleFunc("/products", handlers.getProducts(client, ctx)).Methods("GET")
	rouer.HandleFunc("/products/{id}", handlers.getProductById(client, ctx)).Methods("GET")
	router.HandleFunc("/", showFunctions)

	port := ":8000"
	fmt.Printf("server is running on: http://localhost%s\n", port)
	log.Fatal(http.ListenAndServe(port, router))
}

func showFunctions(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "products/	GET")
	fmt.Fprintln(w, "products/{id}	GET")
}