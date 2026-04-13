package handler

import (
	"encoding/json"
	"log"
	"net/http"
)

func (h *Handler) Registration(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()

	var req RegistrationRequest
	err := json.NewDecoder(r.Body).Decode(&req)
	if err != nil {
		http.Error(w, "invalid request body", http.StatusBadRequest)
		log.Printf("%v", err)
		return
	}

	err = h.service.Registration(ctx, req.Name, req.Email, req.Password)
	if err != nil {
		http.Error(w, "something went wrong. Try again later", http.StatusBadRequest)
		log.Printf("%v", err)
		return
	}
}

func (h *Handler) Login(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()

	var req LoginRequest
	err := json.NewDecoder(r.Body).Decode(&req)
	if err != nil {
		http.Error(w, "invalid request body", http.StatusBadRequest)
		log.Printf("%v", err)
		return
	}

	user, err := h.service.Login(ctx, req.Email, req.Password)
	if err != nil {
		http.Error(w, "something went wrong", http.StatusBadRequest)
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(user)
}

type RegistrationRequest struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Password string `json:"password"`
}

type LoginRequest struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}
