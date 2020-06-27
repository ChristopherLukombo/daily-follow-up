import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { FileService } from "src/app/services/file/file.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-picture-meal-edit",
  templateUrl: "./picture-meal-edit.component.html",
  styleUrls: ["./picture-meal-edit.component.scss"],
})
export class PictureMealEditComponent implements OnInit {
  @Input() content: Content;

  picture: string | ArrayBuffer | null;
  defaultImg: string = "../../../../../../assets/empty_picture.png";
  displayImg: string | ArrayBuffer | null;

  validFile: string[] = [
    "image/jpg",
    "image/jpeg",
    "image/gif",
    "image/png",
    "image/tif",
  ];
  inputError: string;
  file: File = null;
  uploading: boolean = false;

  loading: boolean = false;
  error: string;

  constructor(
    private fileService: FileService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.content) {
      this.loading = true;
      this.fileService.getContentPicture(this.content.id).subscribe(
        (data) => {
          this.generatePicture(data);
          this.loading = false;
        },
        (error) => {
          console.log(error);
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  generatePicture(image: Blob) {
    if (!image.size) {
      this.picture = this.defaultImg;
      return;
    }
    const reader = new FileReader();
    reader.addEventListener(
      "load",
      () => {
        this.picture = reader.result;
      },
      false
    );
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  handleFile(files: FileList) {
    this.inputError = null;
    this.file = null;
    const input = files.item(0);
    if (!input) {
      this.inputError = "Le fichier est requis";
      return;
    }
    if (!this.validExtension(input)) {
      this.inputError = "Le fichier n'est pas une image";
      return;
    }
    this.file = input;
    this.generateDisplay(this.file);
  }

  generateDisplay(image: Blob) {
    if (!image.size) {
      this.displayImg = this.defaultImg;
      return;
    }
    const reader = new FileReader();
    reader.addEventListener(
      "load",
      () => {
        this.displayImg = reader.result;
      },
      false
    );
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  onUpload(): void {
    if (!this.file) return;
    this.uploading = true;
    this.fileService.uploadContentPicture(this.file, this.content.id).subscribe(
      (data) => {
        this.toastrService.success(
          "La photo du plat a été mise à jour",
          "Mise à jour terminée !"
        );
        this.uploading = false;
        this.generatePicture(this.file);
        this.displayImg = null;
      },
      (error) => {
        console.log(error);
        this.toastrService.error(this.getError(error), "Oops !");
        this.uploading = false;
      }
    );
  }

  validExtension(file: File): boolean {
    const type = file.type.toLowerCase();
    return this.validFile.indexOf(type) !== -1;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else if (error && error === 403) {
      return "Vous n'êtes plus habilité à cette requête.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
