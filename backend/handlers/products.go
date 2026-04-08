package handlers

import (
	"encoding/json"
	"log"
	"net/http"
	"cloud.google.com/go/firestore"
	"github.com/gorilla/mux"
	"backend/models"
)

func getProducts(w http.ResponseWriter, r *http.Request, client *firestore.Client, ctx context.Context) {
	log.Print("fetching products")

	collection := client.Collection("products")
	products, err := collection.Documents(ctx).GetAll()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	var result [].models.Product
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

func getProductById(w http.ResponseWriter, r *http.Request, client *firestore.Client, ctx context.Context) {
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

	var product models.Product
	err = doc.DataTo(&product)
	if err != nil {
		http.Error(w, "failed to parse product", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(product)
}