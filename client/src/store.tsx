import {combineReducers} from "redux";
import ColumnTypeDecisionDialogReducer
    from "./component/Toolbar/Upload/MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";

const rootReducer=combineReducers({
    ColumnTypeDecisionDialogReducer
})

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;