const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const User = require("../model/userModel");

// Generate JWT
const generateToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, {
    expiresIn: "30d",
  });
};

// Register a new user
const registerUser = async (req, res) => {
  const { gmail, password, confirmPassword } = req.body;

  // Check if all fields are provided
  if (!gmail || !password || !confirmPassword) {
    return res.status(400).json({ message: "Please fill all fields" });
  }

  // Check if password and confirm password match
  if (password !== confirmPassword) {
    return res.status(400).json({ message: "Passwords do not match" });
  }

  // Check if user already exists
  const userExists = await User.findOne({ gmail });
  if (userExists) {
    return res.status(400).json({ message: "User already exists" });
  }

  // Hash the password
  const salt = await bcrypt.genSalt(10);
  const hashedPassword = await bcrypt.hash(password, salt);

  // Create the user
  const user = await User.create({
    gmail,
    password: hashedPassword,
  });

  if (user) {
    res.status(201).json({
      _id: user.id,
      gmail: user.gmail,
      token: generateToken(user._id),
    });
  } else {
    res.status(400).json({ message: "Invalid user data" });
  }
};

// Login user
const loginUser = async (req, res) => {
  const { gmail, password } = req.body;

  // Check for user in the database
  const user = await User.findOne({ gmail });

  if (user && (await bcrypt.compare(password, user.password))) {
    res.json({
      _id: user.id,
      gmail: user.gmail,
      token: generateToken(user._id),
    });
  } else {
    res.status(400).json({ message: "Please Register before Login" });
  }
};

// Reset password
const resetPassword = async (req, res) => {
  const { gmail, newPassword, confirmNewPassword } = req.body;

  // Check if new passwords match
  if (newPassword !== confirmNewPassword) {
    return res.status(400).json({ message: "Passwords do not match" });
  }

  // Check if user exists
  const user = await User.findOne({ gmail });
  if (!user) {
    return res.status(404).json({ message: "User not found" });
  }

  // Hash the new password
  const salt = await bcrypt.genSalt(10);
  const hashedPassword = await bcrypt.hash(newPassword, salt);

  // Update user password
  user.password = hashedPassword;
  await user.save();

  res.status(200).json({ message: "Password updated successfully" });
};

module.exports = {
  registerUser,
  loginUser,
  resetPassword,
};
