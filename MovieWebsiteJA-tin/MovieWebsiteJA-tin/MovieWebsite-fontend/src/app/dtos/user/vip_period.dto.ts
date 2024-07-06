import {
    IsNumber   
} from 'class-validator';

export class VipPeriodDTO {
    @IsNumber()
    user_id: number;

    constructor(data:any) {
        this.user_id = data.user_id;     
    }
}