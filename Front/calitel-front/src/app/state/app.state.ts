import { RecordsState } from "../models/state/records.state";
import { recordsReducer } from "./reducers/records.reducers";
import { ActionReducerMap } from "@ngrx/store";

export interface AppState {
  records: RecordsState;
}

export const ROOT_REDUCERS: ActionReducerMap<AppState> = {
  records: recordsReducer
};
