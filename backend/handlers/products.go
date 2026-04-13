package handlers

import (
	"backend/models"
	"encoding/json"
	"github.com/gorilla/mux"
	"github.com/jackc/pgx/v5/pgxpool"
	"log"
	"net/http"
)

func GetProducts(conn *pgxpool.Pool) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		ctx := r.Context()

		log.Print("fetching products")

		rows, err := conn.Query(ctx, "select * from products;")
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		defer rows.Close()

		print(rows)

		var products []models.Product

		for rows.Next() {
			var p models.Product

			err := rows.Scan(&p.Id, &p.Name, &p.Colour, &p.Price, &p.Type)
			if err != nil {
				log.Println("scan error:", err)
			}

			products = append(products, p)
		}

		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(products)
	}
}

func GetProductById(conn *pgxpool.Pool) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		ctx := r.Context()
		vars := mux.Vars(r)
		id := vars["id"]

		log.Printf("fetching product by id: %s\n", id)

		row := conn.QueryRow(ctx, "select * from products where id=$1", id)

		var p models.Product
		row.Scan(&p.Id, &p.Name, &p.Colour, &p.Price, &p.Type)

		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(p)
	}
}
