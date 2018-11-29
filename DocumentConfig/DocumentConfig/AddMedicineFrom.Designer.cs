namespace DocumentConfig
{
    partial class AddMedicineFrom
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.name_box = new System.Windows.Forms.TextBox();
            this.func_box = new System.Windows.Forms.TextBox();
            this.documnet_pathbox = new System.Windows.Forms.TextBox();
            this.select_path = new System.Windows.Forms.Button();
            this.close_but = new System.Windows.Forms.Button();
            this.cretae_but = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("宋体", 18F);
            this.label1.Location = new System.Drawing.Point(169, 69);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(178, 24);
            this.label1.TabIndex = 0;
            this.label1.Text = "请填写药品信息";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(132, 120);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "药品名称：";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(132, 166);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 12);
            this.label3.TabIndex = 2;
            this.label3.Text = "工厂信息：";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(132, 218);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(65, 12);
            this.label4.TabIndex = 3;
            this.label4.Text = "模板位置：";
            // 
            // name_box
            // 
            this.name_box.Location = new System.Drawing.Point(189, 117);
            this.name_box.Name = "name_box";
            this.name_box.Size = new System.Drawing.Size(158, 21);
            this.name_box.TabIndex = 4;
            // 
            // func_box
            // 
            this.func_box.Location = new System.Drawing.Point(189, 166);
            this.func_box.Name = "func_box";
            this.func_box.Size = new System.Drawing.Size(158, 21);
            this.func_box.TabIndex = 5;
            // 
            // documnet_pathbox
            // 
            this.documnet_pathbox.Location = new System.Drawing.Point(189, 218);
            this.documnet_pathbox.Name = "documnet_pathbox";
            this.documnet_pathbox.Size = new System.Drawing.Size(158, 21);
            this.documnet_pathbox.TabIndex = 6;
            // 
            // select_path
            // 
            this.select_path.Location = new System.Drawing.Point(375, 215);
            this.select_path.Name = "select_path";
            this.select_path.Size = new System.Drawing.Size(75, 23);
            this.select_path.TabIndex = 7;
            this.select_path.Text = "选择";
            this.select_path.UseVisualStyleBackColor = true;
            this.select_path.Click += new System.EventHandler(this.select_path_Click);
            // 
            // close_but
            // 
            this.close_but.Location = new System.Drawing.Point(134, 286);
            this.close_but.Name = "close_but";
            this.close_but.Size = new System.Drawing.Size(75, 23);
            this.close_but.TabIndex = 8;
            this.close_but.Text = "取消";
            this.close_but.UseVisualStyleBackColor = true;
            this.close_but.Click += new System.EventHandler(this.close_but_Click);
            // 
            // cretae_but
            // 
            this.cretae_but.Location = new System.Drawing.Point(306, 286);
            this.cretae_but.Name = "cretae_but";
            this.cretae_but.Size = new System.Drawing.Size(75, 23);
            this.cretae_but.TabIndex = 9;
            this.cretae_but.Text = "创建";
            this.cretae_but.UseVisualStyleBackColor = true;
            this.cretae_but.Click += new System.EventHandler(this.cretae_but_Click);
            // 
            // AddMedicineFrom
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(584, 362);
            this.Controls.Add(this.cretae_but);
            this.Controls.Add(this.close_but);
            this.Controls.Add(this.select_path);
            this.Controls.Add(this.documnet_pathbox);
            this.Controls.Add(this.func_box);
            this.Controls.Add(this.name_box);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "AddMedicineFrom";
            this.Text = "添加药品";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox name_box;
        private System.Windows.Forms.TextBox func_box;
        private System.Windows.Forms.TextBox documnet_pathbox;
        private System.Windows.Forms.Button select_path;
        private System.Windows.Forms.Button close_but;
        private System.Windows.Forms.Button cretae_but;
    }
}