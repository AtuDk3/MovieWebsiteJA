import{
    IsString,
    IsNotEmpty,
    IsPhoneNumber,
    IsDate
 }from 'class-validator'
 
 export class RegisterDTO{
     @IsString()
     full_name: string;
 
     @IsPhoneNumber()
     phone_number: string;
 
     @IsString()
     @IsNotEmpty()
     password: string;
 
     @IsString()
     @IsNotEmpty()
     retype_password: string;
 
     @IsDate()
     date_of_birth: Date;
 
     @IsString()
     @IsNotEmpty()
     email: string;
 
     constructor(data: any){
         this.full_name= data.fullName;
         this.phone_number= data.phone_number;
         this.password= data.password;
         this.retype_password= data.retype_password;
         this.date_of_birth= data.date_of_birth;
         this.email= data.email;
     }
 }