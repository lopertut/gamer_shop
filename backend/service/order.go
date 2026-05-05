package service

import (
	"backend/model"
	"context"
)

func (s *Service) addOrder(ctx context.Context, order model.Order) error {
	err := s.repo.InsertOrder(ctx, order)
	if err != nil {
		return err
	}

	return nil
}

func (s *Service) addOrderItem(ctx context.Context, orderItem model.OrderItem) error {
	err := s.repo.InsertOrderItem(ctx, orderItem)
	if err != nil {
		return err
	}

	return nil
}

func (s *Service) GetOrderItemsByOrderId(ctx context.Context, id int) ([]model.OrderItem, error) {
	orderItems, err := s.repo.GetOrderItemsByOrderId(ctx, id)
	if err != nil {
		return orderItems, err
	}

	return orderItems, nil
}

func (s *Service) copyCartItems(ctx context.Context, cartItems []model.CartItem) ([]model.OrderItem, error) {
	orderItems := []model.OrderItem{}

	for _, cartItem := range cartItems {
		var orderItem model.OrderItem
		err := cartItem.Scan() & cartItem.ProductId, &cartItem.Name, &cartItem.Quantity, &cartItem.Price

	}

	return orderItems, nil
}

func (s *Service) CreateOrder(ctx context.Context, order model.Order, userId int, cartId int) error {

	err := s.addOrder(ctx, order)
	if err != nil {
		return err
	}

	cartItems, err := s.GetCartItemsByCartId(ctx, cartId)
	if err != nil {
		return err
	}

	orderItems, err := copyCartItems(ctx, cartItems)
	if err != nil {
		return err
	}

	for _, orderItem := range orderItems {
		s.repo.InsertOrderItem(ctx, orderItem)
	}

	err = s.repo.DeleteCartItems(ctx, cartId)
	if err != nil {
		return err
	}

	return nil
}
