package service

import (
	"backend/model"
	"context"
)

func (s *Service) GetCartItemsByCartId(ctx context.Context, cartId int) ([]model.CartItem, error) {
	cartItems, err := s.repo.GetCartItemsByCartId(ctx, cartId)
	if err != nil {
		return []model.CartItem{}, err
	}

	return cartItems, nil
}

func (s *Service) AddCartItem(ctx context.Context, cartItem model.CartItem) error {
	err := s.repo.InsertCartItem(ctx, cartItem)
	if err != nil {
		return err
	}

	return nil
}

func (s *Service) DeleteCartItem(ctx context.Context, id int) error {
	err := s.repo.DeleteCartItem(ctx, id)
	if err != nil {
		return err
	}

	return nil
}

func (s *Service) AddCart(ctx context.Context, userId int) error {
	err := s.repo.InsertCart(ctx, userId)
	if err != nil {
		return err
	}

	return nil
}
