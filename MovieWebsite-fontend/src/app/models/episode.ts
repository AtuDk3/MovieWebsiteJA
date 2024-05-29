export interface Episode {
    id: number;
    create_at: Date;
    update_at: Date;
    movie: {
        id: number;
        name: string;
        description: string;
        image: string;
        slug: string;
        releaseDate: Date;
        duration: string;
        genre: {
            id: number;
            name: string;
            description: string;
            slug: string;
            isActive: number;
        };
        movieType: {
            id: number;
            name: string;
            isActive: number;
        };
        country: {
            id: number;
            name: string;
            isActive: number;
        };
        episode: number;
        hot: number;
        isFee: number;
        season: number;
        limitedAge: number;
        numberView: number;
        isActive: number;
    };
    movieUrl: string;
    episode: number;
}
