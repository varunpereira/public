import mongoose from "mongoose";

const schema = mongoose.Schema(
  {
    username: { type: String, required: true },
    password: { type: String, required: true },
  },
  { collection: "user-model" }
);

export default mongoose.model("user_model", schema);

// make sure no indexes in db other than default id, else will mess things up
