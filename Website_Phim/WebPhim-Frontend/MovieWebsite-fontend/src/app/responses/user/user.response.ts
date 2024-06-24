
import { Role } from "../../models/role";
import { UserVip } from "../../models/user_vip";
import {SafeUrl } from '@angular/platform-browser';
export interface UserResponse{
    id: number;
    full_name:string;
    is_active: number;
    date_of_birth: Date;
    facebook_account_id: number;
    google_account_id: number;
    email: string;
    role: Role;
    phone_number: string;
    img_avatar: string;
    created_at: Date;
    updated_at: Date;
    user_vip: UserVip;
    image_url?: SafeUrl;
}