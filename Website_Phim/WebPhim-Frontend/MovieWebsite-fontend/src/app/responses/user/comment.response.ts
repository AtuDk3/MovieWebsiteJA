import { UserResponse } from "./user.response";

export interface CommentResponse{   
    movie_id: number;
    user_response: UserResponse;
    description: string;
    create_at: Date;
}
