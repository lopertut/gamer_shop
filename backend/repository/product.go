package repository

import (
	"backend/model"
	"context"
	"log"
)

func (r *Repository) GetProducts(ctx context.Context) ([]model.Product, error) {
	var products []model.Product

	rows, err := r.pool.Query(ctx, "select * from get_products;")
	if err != nil {
		return products, err
	}
	defer rows.Close()

	for rows.Next() {
		var p model.Product

		err := rows.Scan(&p.Id, &p.Name, &p.Price, &p.Specifications, &p.Images, &p.CategoryName, &p.BrandName, &p.AvgRating)
		if err != nil {
			log.Println("scan error:", err)
		}

		products = append(products, p)
	}

	return products, nil
}

func (r *Repository) GetProductById(ctx context.Context, id int) (model.Product, error) {
	var p model.Product

	row := r.pool.QueryRow(ctx, "select * from get_products where id=$1;", id)
	err := row.Scan(&p.Id, &p.Name, &p.Price, &p.Specifications, &p.Images, &p.CategoryName, &p.BrandName, &p.AvgRating)
	if err != nil {
		return model.Product{}, err
	}

	return p, nil
}

func (r *Repository) GetProductsByCategory(ctx context.Context, id int) ([]model.Product, error) {
	var products []model.Product
	return products, nil
}
