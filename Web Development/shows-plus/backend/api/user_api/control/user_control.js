import express from "express";
import user_model from "../model/user_model.js";

const router = express.Router();

router.route("/user_create").post(async (req, res) => {
  const user = new user_model({
    username: req.body.username,
    password: req.body.password,
  });
  await user.save();

  res.json(user);
});

router.route("/user_find_all").post(async (req, res) => {
  const allUsers = await user_model.find();
  res.json(allUsers);
});

router.route("/test").post(async (req, res) => {
  res.send("hello");
});

export default router;
