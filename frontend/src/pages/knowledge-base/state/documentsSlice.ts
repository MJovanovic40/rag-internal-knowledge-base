import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

export interface Document {
  id: string;
  name: string;
  createdAt: string;
}

export interface DocumentsState {
  documents: Document[];
}

const initialState: DocumentsState = {
  documents: [],
};

export const documentsSlice = createSlice({
  name: "documents",
  initialState,
  reducers: {
    setDocuments: (state, action: PayloadAction<Document[]>) => {
      state.documents.splice(0);
      state.documents = action.payload;
    },
    removeDocument: (state, action: PayloadAction<Document>) => {
      state.documents.splice(state.documents.indexOf(action.payload), 1);
    },
  },
});

export const { setDocuments, removeDocument } = documentsSlice.actions;

export default documentsSlice.reducer;
