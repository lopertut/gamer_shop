package service

import (
	"backend/model"
	"context"
)

func (s *Service) GetProducts(ctx context.Context) ([]model.Product, error) {
	products, err := s.repo.GetProducts(ctx)
	if err != nil {
		return nil, err
	}

	return products, nil
}

func (s *Service) GetProductById(ctx context.Context, id int) (model.Product, error) {
	product, err := s.repo.GetProductById(ctx, id)
	if err != nil {
		return model.Product{}, err
	}

	return product, nil
}
