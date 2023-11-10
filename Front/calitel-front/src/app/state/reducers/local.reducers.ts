import { ActionReducer } from '@ngrx/store';
import { localStorageSync } from 'ngrx-store-localstorage';
import { LocalStorageEnum } from 'src/app/enums/local-storage.enum';

export function localStorageSyncReducer(reducer: ActionReducer<any>): ActionReducer<any> {
  return localStorageSync({ keys: [LocalStorageEnum.RecordsStateKey], rehydrate: true })(reducer);
}
