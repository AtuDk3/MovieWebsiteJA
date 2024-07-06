import { Genre } from "../../models/genre"; 

export interface FavouriteResponse{  
    image: string
    movie_id: number
    movie_name: string
    genre: Genre
    genre_name: string
    url: string
}