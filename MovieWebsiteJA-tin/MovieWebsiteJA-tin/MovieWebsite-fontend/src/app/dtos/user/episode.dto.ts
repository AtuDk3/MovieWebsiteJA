import {
    IsString,
    IsNumber,
} from 'class-validator';

export class EpisodeDTO {
    
    @IsNumber()
    movie_id: number;

    @IsNumber()
    episode: number;


    @IsString()
    movie_url: string;

    constructor(data:any) {
        this.movie_id = data.movie_id;
        this.episode =  data.episode;
        this.movie_url= data.movie_url
    }
}