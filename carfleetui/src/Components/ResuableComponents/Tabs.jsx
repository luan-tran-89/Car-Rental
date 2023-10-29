import { Grid, Button } from "@mui/material";

function Tabs({ tabs, activeTabIndex, onTabChange, tabStyles }) {
  return (
    <Grid container="true" sx={{ marginTop: "1em", marginBottom: "1em" }}>
      {tabs.map((tab, index) => (
        <Grid
          item
          md={3}
          xs={12}
          style={tabStyle(index, activeTabIndex, tabStyles)}
          key={index}
        >
          <Button
            onClick={() => onTabChange(index)}
            variant="outline"
            startIcon={tab.icon}
            size="large"
          >
            {tab.label}
          </Button>
        </Grid>
      ))}
    </Grid>
  );
}

function tabStyle(index, activeTabIndex, customStyles = {}) {
  return {
    cursor: "pointer",
    color: activeTabIndex === index ? "red" : "black",
    textAlign: "center",
    ...customStyles,
  };
}

export default Tabs;
