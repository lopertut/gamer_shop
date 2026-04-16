package service

import (
	"backend/model"
	"context"
	"golang.org/x/crypto/bcrypt"
	"log"
)

func (s *Service) Registration(ctx context.Context, name string, email string, password string) error {

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		log.Printf("%v", err)
		return err
	}

	user := model.User{
		Name:     name,
		Email:    email,
		Password: string(hashedPassword),
	}

	newUser, err := s.repo.InsertUser(ctx, user)
	if err != nil {
		log.Printf("%v", err)
		return err
	}

	err = s.repo.InsertCart(ctx, newUser.Id)
	if err != nil {
		return err
	}

	return nil
}

func (s *Service) Login(ctx context.Context, email string, password string) (model.User, error) {
	user, err := s.repo.GetUserByEmail(ctx, email)
	if err != nil {
		return model.User{}, err
	}

	err = bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(password))
	if err != nil {
		return model.User{}, err
	}

	return user, nil
}
