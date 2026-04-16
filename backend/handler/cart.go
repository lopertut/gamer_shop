package handler

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"strconv"
)

func (h *Handler) GetCartItemsByCartId(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	vars := mux.Vars(r)
	strId := vars["cart_id"]

	id, err := strconv.ParseInt(strId, 10, 64)
	if err != nil {
		http.Error(w, "invalid cart_id", http.StatusBadRequest)
		return
	}

	cartItems, err := h.service.GetCartItemsByCartId(ctx, int(id))
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(cartItems)
}

func (h *Handler) AddCartItem(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	var req InsertItemForm

	err := json.NewDecoder(r.Body).Decode(&req)
	if err != nil {
		http.Error(w, "invalid request body", http.StatusBadRequest)
		log.Printf("%v", err)
		return
	}

	err = h.service.AddCartItem(ctx, req.CartId, req.ProductId, req.Quantity)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("item added successfully"))
}

func (h *Handler) DeleteCartItem(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	vars := mux.Vars(r)
	strId := vars["id"]
	id, err := strconv.ParseInt(strId, 10, 64)
	if err != nil {
		http.Error(w, "invalid cart_item id", http.StatusBadRequest)
		return
	}

	err = h.service.DeleteCartItem(ctx, int(id))
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("item deleted successfully"))
}

type InsertItemForm struct {
	CartId    int `json:"cart_id"`
	ProductId int `json:"product_id"`
	Quantity  int `json:"quantity"`
}
