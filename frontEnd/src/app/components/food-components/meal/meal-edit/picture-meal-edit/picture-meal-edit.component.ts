import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { FileService } from "src/app/services/file/file.service";
import { ToastrService } from "ngx-toastr";
import { ArtificialIntelligenceService } from "src/app/services/artificial-intelligence/artificial-intelligence.service";
import { TypeMessage } from "src/app/models/utils/message-enum";

@Component({
  selector: "app-picture-meal-edit",
  templateUrl: "./picture-meal-edit.component.html",
  styleUrls: ["./picture-meal-edit.component.scss"],
})
export class PictureMealEditComponent implements OnInit {
  @Input() content: Content;
  defaultImg: string = "../../../../../../assets/empty_picture.png";

  picture: string | ArrayBuffer | null;
  futurePicture: string | ArrayBuffer | null;

  suggestions: (string | ArrayBuffer | null)[] = [];
  searching: boolean = false;

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
    private artificialIntelligenceService: ArtificialIntelligenceService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.content) {
      this.loading = true;
      this.fileService.getContentPicture(this.content.id).subscribe(
        (data) => {
          this.loadPicture(data);
          this.getSuggestions();
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  loadPicture(blob: Blob) {
    if (!blob.size) {
      this.picture = this.defaultImg;
      return;
    }
    const reader = new FileReader();
    reader.onload = (e) => {
      this.picture = reader.result;
    };
    if (blob) reader.readAsDataURL(blob);
  }

  loadFuturePicture(blob: Blob) {
    if (!blob.size) {
      this.futurePicture = this.defaultImg;
      return;
    }
    const reader = new FileReader();
    reader.onload = (e) => {
      this.futurePicture = reader.result;
    };
    if (blob) reader.readAsDataURL(blob);
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
    this.loadFuturePicture(this.file);
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
        this.loadPicture(this.file);
        this.futurePicture = null;
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

  getSuggestions(): void {
    this.searching = true;
    let name: string = this.content.name.split(" ")[0];
    this.artificialIntelligenceService
      .getOutputPicturesFromModel(name)
      .subscribe(
        (data) => {
          this.loadSuggestions(data);
          this.searching = false;
        },
        (error) => {
          console.log(error);
          this.searching = false;
        }
      );
  }

  loadSuggestions(blobs: Blob[]) {
    for (let i = 0; i < blobs.length; i++) {
      if (!blobs[i]) {
        continue;
      }
      const reader = new FileReader();
      reader.onload = (e) => {
        this.suggestions[i] = reader.result;
      };
      reader.readAsDataURL(blobs[i]);
    }
  }

  selectSuggestion(base64Image: string): void {
    let blob = this.artificialIntelligenceService.convertBase64ToBlob(
      base64Image.split(",")[1]
    );
    this.file = new File([blob], "content_" + this.content.id + ".png", {
      type: "image/png",
      lastModified: Date.now(),
    });
    this.loadFuturePicture(blob);
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error === 403) {
      return TypeMessage.NOT_AUTHORIZED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
