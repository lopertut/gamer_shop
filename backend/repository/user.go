package repository

import (
	"backend/model"
	"context"
)

func (r *Repository) InsertUser(ctx context.Context, user model.User) (model.User, error) {
	var newUser model.User

	query := "insert into users(name, email, password) values($1, $2, $3) returning id, name, email;"
	row := r.pool.QueryRow(ctx, query, user.Name, user.Email, user.Password)
	err := row.Scan(&newUser.Id, &newUser.Name, &newUser.Email)
	if err != nil {
		return model.User{}, err
	}

	return newUser, err
}

func (r *Repository) GetUserByEmail(ctx context.Context, email string) (model.User, error) {
	var user model.User

	query := "select name, email, password from users where email=$1;"
	row := r.pool.QueryRow(ctx, query, email)
	err := row.Scan(&user.Name, &user.Email, &user.Password)
	if err != nil {
		return model.User{}, err
	}

	return user, nil
}
