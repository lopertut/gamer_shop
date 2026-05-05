package handler

import (
	"encoding/json"
	"net/http"

	"github.com/gorilla/mux"
	"strconv"
)

func (h *Handler) GetOrderItemsByOrderId(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	vars := mux.Vars(r)
	strId := vars["order_id"]
	id, err := strconv.ParseInt(strId, 10, 64)
	if err != nil {
		http.Error(w, "invalid order id", http.StatusBadRequest)
		return
	}

	cartItems, err := h.service.GetOrderItemsByOrderId(ctx, int(id))
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(cartItems)
}

func (h *Handler) CreateOrder(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	userId := ctx.Value("user_id").(int)
	cartId := ctx.Value("cart_id").(int)

	err := h.service.CreateOrder(ctx, userId, cartId)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("order added successfully"))
}
