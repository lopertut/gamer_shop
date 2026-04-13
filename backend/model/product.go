package model

type Product struct {
	Id     int    `json:"id"`
	Name   string `json:"name"`
	Type   string `json:"type"`
	Colour string `json:"colour"`
	Price  string `json:"price"`
}
