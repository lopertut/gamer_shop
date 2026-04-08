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
)

var (
	ctx       context.Context
	client    *firestore.Client
	err       error	
	projectId = os.Getenv("project_id")
)

type Product struct {
	Name   string `json:"name"`
	Type   string `json:"type"`
	Colour string `json:"colour"`
	Price  string `json:"price"`
}

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

	router.HandleFunc("/products", getProducts).Methods("GET")
	rouer.HandleFunc("/products/{id}", getProductById).Methods("GET")
	router.HandleFunc("/", helloWorld)

	port := ":8000"
	fmt.Printf("server is running on: http://localhost%s\n", port)
	log.Fatal(http.ListenAndServe(port, router))
}

func getProducts(w http.ResponseWriter, r *http.Request) {
	log.Print("fetching products")

	collection := client.Collection("products")
	products, err := collection.Documents(ctx).GetAll()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	var result []Product
	for _, doc := range products {
		var product Product
		err := doc.DataTo(&product)
		if err != nil {
			log.Printf("failed to convert doc: %v\n", err)
			continue
		}
		result = append(result, product)
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(result)
}

func getProductById(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id := vars["id"]

	log.Printf("fetching product by id: %s\n", id)
	
	collection := client.Collection("products")
	doc, err := collection.Doc(id).Get(ctx)
	if err != nil { 
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	if !doc.Exists() {
		http.Error(w, "product not found", http.StatusNotFound)
		return
	}

	var product Product
	err = doc.DataTo(&product)
	if err != nil {
		http.Error(w, "failed to parse product", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(product)
}

func helloWorld(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "hello world")
}
