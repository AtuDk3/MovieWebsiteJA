
export interface Movie {
    id: number,
    name: string,
    description: string,
    image: string,
    slug: string,
    release_date: number,
    duration: string,
    id_genre: number,
    id_movie_type: number,
    id_country: number,
    episode: number,
    hot: number,
    is_fee: number,
    season: number,
    limited_age: number,
    number_views: number,
    movie_type_name: string,
    genre_name: string,
    country_name: string,
    url: string
    release_date_formated: string
    is_active: number
}