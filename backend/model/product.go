package model

type Product struct {
	Id             int            `json:"id"`
	Name           string         `json:"name"`
	CategoryName   string         `json:"category_name"`
	Price          float64        `json:"price"`
	BrandName      string         `json:"brand_name"`
	AvgRating      float64        `json:"avg_rating"`
	Specifications map[string]any `json:"specifications"`
}
