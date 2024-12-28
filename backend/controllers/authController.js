const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const User = require("../model/userModel");

// Utility function to generate JWT token
const generateToken = (userId) => {
  return jwt.sign({ id: userId }, process.env.JWT_SECRET, {
    expiresIn: "30d", // Token expiration time
  });
};

// Register a new user
const registerUser = async (req, res) => {
  const { gmail, password, confirmPassword, name, imgUrl } = req.body;

  try {
    // Check if all fields are provided
    if (!gmail || !password || !confirmPassword || !name) {
      return res.status(400).json({ message: "Please fill all fields" });
    }

    // Check if passwords match
    if (password !== confirmPassword) {
      return res.status(400).json({ message: "Passwords do not match" });
    }

    // Check if user already exists
    const userExists = await User.findOne({ gmail });
    if (userExists) {
      return res.status(400).json({ message: "User already exists" });
    }

    // Hash password
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    // Create new user
    const user = await User.create({
      gmail,
      password: hashedPassword,
      name,
      imgUrl, // Add the imgUrl field
    });

    // Generate token
    const token = generateToken(user._id);

    // Save token to the user document
    user.token = token;
    await user.save();

    // Return user data and token
    res.status(201).json({
      id: user._id,
      gmail: user.gmail,
      name: user.name,
      imgUrl: user.imgUrl,
      token,
    });
  } catch (error) {
    res.status(500).json({ message: "Server error, please try again" });
  }
};

// Login user
const loginUser = async (req, res) => {
  const { gmail, password } = req.body;

  try {
    // Find user by email (gmail)
    const user = await User.findOne({ gmail });
    if (!user) {
      return res
        .status(400)
        .json({ message: "Invalid credentials. Please Register first." });
    }

    // Compare password
    if (await bcrypt.compare(password, user.password)) {
      const token = generateToken(user._id);

      // Save token to user document
      user.token = token;
      await user.save();

      // Return user data and token
      res.json({
        id: user._id,
        gmail: user.gmail,
        name: user.name,
        imgUrl: user.imgUrl,
        token,
      });
    } else {
      res
        .status(400)
        .json({ message: "Invalid credentials. Please check your password." });
    }
  } catch (error) {
    res.status(500).json({ message: "Server error, please try again" });
  }
};

// Update user details (name, imgUrl)
const updateUser = async (req, res) => {
  const { name, imgUrl } = req.body;

  try {
    // Find user by ID
    const user = await User.findById(req.user.id); // Assume the user ID is passed in the token
    if (!user) {
      return res.status(404).json({ message: "User not found" });
    }

    // Update user details
    if (name) user.name = name;
    if (imgUrl) user.imgUrl = imgUrl;

    await user.save();

    res.status(200).json({
      message: "User updated successfully",
      user: { name: user.name, imgUrl: user.imgUrl },
    });
  } catch (error) {
    res.status(500).json({ message: "Server error, please try again" });
  }
};

// Delete user account
const deleteUser = async (req, res) => {
  try {
    // Find user by ID
    const user = await User.findByIdAndDelete(req.user.id); // Assume the user ID is passed in the token
    if (!user) {
      return res.status(404).json({ message: "User not found" });
    }

    res.status(200).json({ message: "User deleted successfully" });
  } catch (error) {
    res.status(500).json({ message: "Server error, please try again" });
  }
};

// Reset password
const resetPassword = async (req, res) => {
  const { gmail, newPassword, confirmNewPassword } = req.body;

  try {
    // Check if new passwords match
    if (newPassword !== confirmNewPassword) {
      return res.status(400).json({ message: "Passwords do not match" });
    }

    // Find user by email (gmail)
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
  } catch (error) {
    res.status(500).json({ message: "Server error, please try again" });
  }
};

module.exports = {
  registerUser,
  loginUser,
  updateUser,
  deleteUser,
  resetPassword,
};
