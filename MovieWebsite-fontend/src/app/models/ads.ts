export interface Ads {
    id: number,
    name: string,
    description: string,
    banner_ads: string,
    create_at: Date,
    expiration_at: Date,
    amount: number,
    create_at_formated: string,
    expiration_at_formated: string,
    url: string,
    is_active: number
}