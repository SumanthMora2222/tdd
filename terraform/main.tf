provider "google" {
  project = "otto-university-302315"
  region  = "europe-west3"
}

terraform {
  backend "gcs" {
    bucket = "sumanth-tfstate-bucket"
    prefix = "tdd"
  }
}