package models

type Product struct {
	Name   string `json:"name"`
	Type   string `json:"type"`
	Colour string `json:"colour"`
	Price  string `json:"price"`
}