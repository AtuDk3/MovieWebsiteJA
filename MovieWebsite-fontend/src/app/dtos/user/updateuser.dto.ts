import{
    IsString,
    IsNotEmpty,
    IsPhoneNumber,
    IsDate,
    IsNumber
    
 }from 'class-validator'
 
 export class UpdateUserDTO{
     
    @IsString()
     full_name: string | null;
 
    //  @IsPhoneNumber()
    //  phone_number: string | null;
     
    //  @IsString()
    //  email: string | null;

     @IsString()
     vip_name: string | null;
    
     constructor(data: any){
         this.full_name= data.fullName;
        //  this.phone_number= data.phone_number;       
        //  this.email= data.email; 
         this.vip_name= data.vip_name;       
     }
 }