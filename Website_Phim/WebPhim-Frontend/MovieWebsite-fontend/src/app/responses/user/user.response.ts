
import { Role } from "../../models/role";
import { UserVip } from "../../models/user_vip";

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
    created_at: Date;
    created_at_formatted: string;
    date_of_birth_formatted: string;
    user_vip: UserVip;
}
