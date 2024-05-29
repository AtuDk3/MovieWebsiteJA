import { Role } from "../../models/role";

export interface UserResponse{
    id: number;
    full_name:string;
    is_active: number;
    password: string;
    date_of_birth: Date;
    facebook_account_id: number;
    google_account_id: number;
    email: string;
    role: Role;
    phone_number: string;
    img_avatar: string;
}