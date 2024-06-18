import{
    IsNumber
 }from 'class-validator'
 
 export class OrderDTO{
     
    @IsNumber()
     user_id: number;

    @IsNumber()
     price: number;

    
     constructor(data: any){
        this.user_id= data.user_id 
        this.price= data.price        
     }
 }