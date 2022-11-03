import express from "express";
import cors from "cors";
import user_control from "./api/user_api/control/user_control.js";
import mongoose from "mongoose";

const app = express();
const port = process.env.PORT || 4000;

// server connection
app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});

// middleware with backend routing
app.use(cors());
app.use(express.json());
// server protocols (req,res), can use Postman
// use send for prim, or json for json
app.use("/api/user", user_control);
app.use("*", (req, res) => res.status(404).json({ error: "not found" }));

// db connection mongodb via mongoose
mongoose.connect("mongodb://localhost:27017/mern-web-app-1");

export default app;
