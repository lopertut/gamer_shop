package repository

import (
	"backend/model"
	"context"
	"log"
)

func (r *Repository) GetProducts(ctx context.Context) ([]model.Product, error) {
	var products []model.Product

	rows, err := r.pool.Query(ctx, "select * from products;")
	if err != nil {
		return products, err
	}
	defer rows.Close()

	for rows.Next() {
		var p model.Product

		err := rows.Scan(&p.Id, &p.Name, &p.Colour, &p.Price, &p.Type)
		if err != nil {
			log.Println("scan error:", err)
		}

		products = append(products, p)
	}

	return products, nil
}

func (r *Repository) GetProductById(ctx context.Context, id int) (model.Product, error) {
	var p model.Product

	row := r.pool.QueryRow(ctx, "select * from products where id=$1;", id)
	err := row.Scan(&p.Id, &p.Name, &p.Colour, &p.Price, &p.Type)
	if err != nil {
		return model.Product{}, err
	}

	return p, nil
}
