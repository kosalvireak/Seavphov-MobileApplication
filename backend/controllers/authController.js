const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const User = require("../model/userModel");
const ApiResponse = require("../utils/ApiResponse"); // Import the ApiResponse class

const defaultProfileUrl =
  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

// Utility function to generate JWT token
const generateToken = (userId) => {
  return jwt.sign({ id: userId }, process.env.JWT_SECRET, {
    expiresIn: "30d", // Token expiration time
  });
};

// Utility function to extract name from email
const splitBeforeAt = (email) => {
  const atIndex = email.indexOf("@");
  if (atIndex === -1) {
    return email;
  }
  return email.substring(0, atIndex);
};

// Register a new user
const registerUser = async (req, res) => {
  const { gmail, password, confirmPassword } = req.body;

  try {
    if (!gmail || !password || !confirmPassword) {
      return res
        .status(400)
        .json(new ApiResponse(400, "Please fill all fields"));
    }

    if (password !== confirmPassword) {
      return res
        .status(400)
        .json(new ApiResponse(400, "Passwords do not match"));
    }

    const userExists = await User.findOne({ gmail });
    if (userExists) {
      return res
        .status(400)
        .json(new ApiResponse(400, "User already exists. Please login!"));
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    const user = await User.create({
      gmail,
      password: hashedPassword,
      name: splitBeforeAt(gmail),
      imgUrl: defaultProfileUrl,
    });

    const token = generateToken(user._id);
    user.token = token;
    await user.save();

    return res.status(201).json(
      new ApiResponse(200, "Registration successful", {
        gmail: user.gmail,
        name: user.name,
        imgUrl: user.imgUrl,
        token,
      })
    );
  } catch (error) {
    return res
      .status(500)
      .json(new ApiResponse(500, "Server error, please try again"));
  }
};

// Login a user
const loginUser = async (req, res) => {
  const { gmail, password } = req.body;

  try {
    if (!gmail || !password) {
      return res
        .status(400)
        .json(new ApiResponse(400, "Please fill all fields"));
    }

    const user = await User.findOne({ gmail });
    if (!user) {
      return res.status(400).json(new ApiResponse(400, "Invalid credentials"));
    }

    const isPasswordMatch = await bcrypt.compare(password, user.password);
    if (!isPasswordMatch) {
      return res.status(400).json(new ApiResponse(400, "Invalid credentials"));
    }

    const token = generateToken(user._id);
    return res.status(200).json(
      new ApiResponse(200, "Login successful", {
        gmail: user.gmail,
        name: user.name,
        imgUrl: user.imgUrl,
        token,
      })
    );
  } catch (error) {
    return res
      .status(500)
      .json(new ApiResponse(500, "Server error, please try again"));
  }
};

// Reset password
const resetPassword = async (req, res) => {
  const { gmail, newPassword } = req.body;

  try {
    if (!gmail || !newPassword) {
      return res
        .status(400)
        .json(new ApiResponse(400, "Please fill all fields"));
    }

    const user = await User.findOne({ gmail });
    if (!user) {
      return res.status(404).json(new ApiResponse(404, "User not found"));
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(newPassword, salt);
    user.password = hashedPassword;

    await user.save();

    return res
      .status(200)
      .json(new ApiResponse(200, "Password reset successfully"));
  } catch (error) {
    return res
      .status(500)
      .json(new ApiResponse(500, "Server error, please try again"));
  }
};

module.exports = {
  registerUser,
  loginUser,
  resetPassword,
};
