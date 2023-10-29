import React from "react";
import Button from "@mui/material/Button";

import { styled } from "@mui/material/styles";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";

const VisuallyHiddenInput = styled("input")({
  clip: "rect(0 0 0 0)",
  clipPath: "inset(50%)",
  height: 1,
  overflow: "hidden",
  position: "absolute",
  bottom: 0,
  left: 0,
  whiteSpace: "nowrap",
  width: 1,
});

export default function InputFileUpload() {
  return (
    <Button
      component="label"
      variant="outlined"
      startIcon={<CloudUploadIcon />}
      // fullWidth
    >
      Upload Car Image
      <VisuallyHiddenInput type="file" />
    </Button>
  );
}
