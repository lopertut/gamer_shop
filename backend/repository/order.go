package repository

import (
	"backend/model"
	"context"
	"log"
)

func (r *Repository) InsertOrder(ctx context.Context, order model.Order) error {
	query := "insert into orders(country, address, postal_code, firstname, lastname, email, total_price)"

	_, err := r.pool.Exec(ctx, query, &order.Country, &order.Address, &order.PostalCode, &order.FirstName, &order.LastName, &order.Email, &order.TotalPrice)
	if err != nil {
		return err
	}

	return nil
}

// TODO: maybe insert list of orderItems
func (r *Repository) InsertOrderItem(ctx context.Context, orderItem model.OrderItem) error {
	query := "insert into order_items(order_id, product_id, product_name, quantity, price)"

	_, err := r.pool.Exec(ctx, query, &orderItem.OrderId, &orderItem.ProductId, &orderItem.ProductName, &orderItem.Quantity, &orderItem.Price)
	if err != nil {
		return err
	}

	return nil
}

func (r *Repository) GetOrderItemsByOrderId(ctx context.Context, id int) ([]model.OrderItem, error) {
	rows, err := r.pool.Query(ctx, "select * from order_items where order_id=$1", id)
	if err != nil {
		return []model.OrderItem{}, err
	}

	orderItems := []model.OrderItem{}

	for rows.Next() {
		var orderItem model.OrderItem

		err = rows.Scan(&orderItem.OrderId, &orderItem.ProductId, &orderItem.ProductName, &orderItem.Quantity, &orderItem.Price)
		if err != nil {
			log.Println("scan error", err)
		}

		orderItems = append(orderItems, orderItem)
	}

	return orderItems, nil
}

func (r *Repository) GetOrders(ctx context.Context, userId int) ([]model.Order, error) {
	rows, err := r.pool.Query(ctx, "select * from orders where user_id=$1", userId)
	if err != nil {
		return []model.Order{}, err
	}

	orders := []model.Order{}

	for rows.Next() {
		var order model.Order

		err = rows.Scan(&order.UserId, &order.CreatedAt, &order.Country, &order.Address, &order.PostalCode, &order.FirstName, &order.LastName, &order.Email, &order.TotalPrice)
		if err != nil {
			log.Println("scan error", err)
		}

		orders = append(orders, order)
	}

	return orders, nil
}
