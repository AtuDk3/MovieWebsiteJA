import{
    IsNumber
 }from 'class-validator'
 
 export class RateDTO{
     
    @IsNumber()
     movie_id: number;

    @IsNumber()
     user_id: number;

    @IsNumber()
    number_stars: number;
        
     constructor(data: any){
        this.movie_id= data.movie_id 
        this.user_id= data.user_id
        this.number_stars= data.number_stars
     }
 }