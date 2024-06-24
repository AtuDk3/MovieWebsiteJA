import { SafeUrl } from "@angular/platform-browser";

export interface Ads {
    id: number,
    name: string,
    description: string,
    trading_code: string,
    email: string,
    list_img: string[],
    list_img_url: SafeUrl[],
    create_at: Date,
    expiration_at: Date,
    amount: number,
    is_active: number,
    is_confirm: number,
    number_days: number
}
