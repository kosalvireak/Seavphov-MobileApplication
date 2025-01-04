const express = require("express");
const {
  registerUser,
  loginUser,
  resetPassword,
} = require("../controllers/authController");

const router = express.Router();

// Route to register a user
router.post("/register", registerUser);

// Route to login a user
router.post("/login", loginUser);

// Route to reset password
router.post("/reset-password", resetPassword);

module.exports = router;
