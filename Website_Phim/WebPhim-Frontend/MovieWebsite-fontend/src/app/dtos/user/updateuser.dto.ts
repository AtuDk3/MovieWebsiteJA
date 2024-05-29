import{
    IsString,
    IsNotEmpty,
    IsPhoneNumber,
    IsDate
    
 }from 'class-validator'
 
 export class UpdateUserDTO{
     
    @IsString()
     full_name: string;
 
     @IsPhoneNumber()
     phone_number: string;
 
     @IsString()
     password: string;

     @IsString()
     email: string;
    
     constructor(data: any){
         this.full_name= data.fullName;
         this.phone_number= data.phone_number;
         this.password= data.password;
         this.email= data.email;        
     }
 }