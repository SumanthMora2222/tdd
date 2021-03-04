provider "google" {
  project = "otto-university-1"
  region  = "europe-west3"
}

terraform {
  backend "gcs" {
    bucket = "sumanth-tfstate-bucket-gcp-training"
    prefix = "tdd"
  }
}