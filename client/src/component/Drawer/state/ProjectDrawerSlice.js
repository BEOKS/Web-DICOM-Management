import {OPEN_PROJECT_DRAWER,CLOSE_PROJECT_DRAWER} from "./ProjectDrawerActions";

const projectDrawerOpenInitialState='close';
export const projectDrawerReducer=(state=projectDrawerOpenInitialState,action)=>{
    switch (action.type){
        case OPEN_PROJECT_DRAWER:
            return 'open'
        case CLOSE_PROJECT_DRAWER:
            return 'close'
        default:
            return state
    }
}