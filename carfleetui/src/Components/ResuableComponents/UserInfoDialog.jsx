import * as React from "react";
import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import Typography from "@mui/material/Typography";
import { Grid } from "@mui/material";

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  "& .MuiDialogContent-root": {
    padding: theme.spacing(2),
  },
  "& .MuiDialogActions-root": {
    padding: theme.spacing(1),
  },
}));

const CustomGrid = styled(Grid)({
  justifyContent: "space-around",
});

export default function UserInfoDialog({
  open,
  handleClose,
  firstName,
  lastName,
  phone,
  email,
  username,
  showRentalHistoryButton = true,
}) {
  return (
    <div>
      <BootstrapDialog
        onClose={handleClose}
        aria-labelledby="customized-dialog-title"
        open={open}
      >
        <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
          Antony
        </DialogTitle>
        <IconButton
          aria-label="close"
          onClick={handleClose}
          sx={{
            position: "absolute",
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
        <DialogContent dividers>
          <CustomGrid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Typography variant="p">
                <b>FirstName:</b> {firstName}
              </Typography>
            </Grid>
            <Grid item xs={12} md={6}>
              <Typography variant="p">
                {" "}
                <b>LastName:</b> {lastName}
              </Typography>
            </Grid>
            <Grid item xs={12} md={6}>
              <Typography variant="p">
                {" "}
                <b>Phone:</b> {phone}
              </Typography>
            </Grid>
            <Grid item xs={12} md={6}>
              <Typography variant="p">
                <b>Email:</b> {email}
              </Typography>
            </Grid>
            <Grid item xs={12} md={12}>
              <Typography variant="p">
                <b>Username:</b> {username}
              </Typography>
            </Grid>

            {showRentalHistoryButton && (
              <Grid item xs={12} md={6}>
                <Button variant="outlined">Get Rental History</Button>
              </Grid>
            )}
            <Grid item xs={12} md={6}>
              <Button variant="outlined">Disable</Button>
            </Grid>
          </CustomGrid>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            Save changes
          </Button>
        </DialogActions>
      </BootstrapDialog>
    </div>
  );
}
